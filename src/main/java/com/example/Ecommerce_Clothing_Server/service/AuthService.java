package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.dto.request.auth.RegisterRequestDTO;
import com.example.Ecommerce_Clothing_Server.dto.response.JwtResponseDTO;
import com.example.Ecommerce_Clothing_Server.entity.*;
import com.example.Ecommerce_Clothing_Server.enums.ExceptionCode;
import com.example.Ecommerce_Clothing_Server.enums.RoleType;
import com.example.Ecommerce_Clothing_Server.exception.SharedException;
import com.example.Ecommerce_Clothing_Server.repository.AdminRepository;
import com.example.Ecommerce_Clothing_Server.repository.AuthRepository;
import com.example.Ecommerce_Clothing_Server.repository.CustomerRepository;
import com.example.Ecommerce_Clothing_Server.repository.VendorRepository;
import com.example.Ecommerce_Clothing_Server.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            AuthenticationManager authenticationManager,
            AuthRepository authRepository,
            CustomerRepository customerRepository,
            VendorRepository vendorRepository,
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.authRepository = authRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 使用者登入，驗證帳號密碼並產生 JWT
    public JwtResponseDTO authenticate(String email, String password) {
        try {
            // 驗證帳號密碼
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // 查使用者資訊（用來產生 JWT）
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // 產生 JWT Access Token
            final String token = jwtService.generateToken(userDetails);

            // 產生 DB 持久 Refresh Token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).findFirst().orElse("GUEST");

            return new JwtResponseDTO(token, refreshToken.getToken(), email, RoleType.valueOf(role));

        } catch (BadCredentialsException e) {
            throw new SharedException("密碼錯誤", ExceptionCode.INVALID_CREDENTIALS, e);
        } catch (SharedException e) {
            throw e;
        } catch (Exception e) {
            throw new SharedException("驗證失敗", ExceptionCode.AUTH_FAILED, e);
        }
    }

    // 刷新 Access token 並且 rotate refresh token
    public JwtResponseDTO refreshToken(String refreshTokenValue) {
        RefreshToken token = refreshTokenService.findByToken(refreshTokenValue)
                .orElseThrow(() -> new SharedException("Refresh token not found", ExceptionCode.USER_NOT_FOUND));

        // 驗證過期 (過期拋例外並刪除)
        refreshTokenService.verifyExpiration(token);

        // 由 token 取得 UserDetails
        var user = token.getUser();
        String email = user.getEmail();
        String role = user.getUserType();

        // 刪除舊的 refresh token (rotation)，並建立新的 refresh token
        refreshTokenService.logoutSingleDevice(refreshTokenValue);
        RefreshToken newRefresh = refreshTokenService.createRefreshToken(email);

        // 產生新的 access token (使用 JwtService 新增的 overload)
        String newAccess = jwtService.generateToken(email, role);

        return new JwtResponseDTO(newAccess, newRefresh.getToken(), email, RoleType.valueOf(role));
    }

    // 登出單一裝置
    public void logoutSingleDevice(String refreshTokenValue) {
        refreshTokenService.logoutSingleDevice(refreshTokenValue);
    }

    // 登出所有裝置
    public void logoutAllDevices(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }

    public boolean checkUserExists(String email) {
        return authRepository.existsByEmail(email);
    }

    public boolean checkUserExists(Long userId) {
        return authRepository.existsById(userId);
    }

    @Transactional // 非常重要！確保資料一致性
    public void register(RegisterRequestDTO requestDTO) {
        // 檢查 Email 是否已存在
        if (authRepository.existsByEmail(requestDTO.getEmail())) {
            throw new SharedException("此 Email 已經被註冊", ExceptionCode.USER_ALREADY_EXISTS);
        }

        RoleType role;
        try {
            role = RoleType.valueOf(requestDTO.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new SharedException("無效的角色", ExceptionCode.INVALID_ROLE);
        }

        // 建立並儲存 User 物件
        User newUser = new User();
        newUser.setEmail(requestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        newUser.setUserType(role); // 假設 User entity 有 setUserType(RoleType role)

        User savedUser = authRepository.save(newUser); // 先儲存 User 以獲得 ID

        // 根據角色，建立並儲存對應的附屬物件
        switch (role) {
            case CUSTOMER:
                Customer customer = new Customer();
                customer.setUser(savedUser); // 關鍵：設定關聯
                customer.setDefaultShippingAddress(requestDTO.getDefaultShippingAddress());
                customer.setBillingAddress(requestDTO.getBillingAddress());
                customerRepository.save(customer);
                break;
            case VENDOR:
                Vendor vendor = new Vendor();
                vendor.setUser(savedUser); // 關鍵：設定關聯
                vendor.setStoreAddress(requestDTO.getStoreAddress());
                vendor.setStoreDescription(requestDTO.getStoreDescription());
                vendor.setStoreLogoUrl(requestDTO.getStoreLogoUrl());
                vendor.setPaymentAccount(requestDTO.getPaymentAccount());
                vendorRepository.save(vendor);
                break;
            case ADMIN:
                Admin admin = new Admin();
                admin.setUser(savedUser); // 關鍵：設定關聯
                adminRepository.save(admin);
                break;
            default:
                throw new SharedException("不支援的角色類型", ExceptionCode.INVALID_ROLE);
        }
    }
}
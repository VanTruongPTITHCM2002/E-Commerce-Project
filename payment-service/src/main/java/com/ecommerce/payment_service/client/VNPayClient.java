package com.ecommerce.payment_service.client;

import com.ecommerce.payment_service.dto.reponse.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

@Component
@RequiredArgsConstructor
public class VNPayClient {

    @Value("${vnpay.tmn-code}")
    private String vnp_TmnCode;

    @Value("${vnpay.hash-secret}")
    private String vnp_HashSecret;

    @Value("${vnpay.url}")
    private String vnp_PayUrl;

    @Value("${vnpay.return-url}")
    private String vnp_ReturnUrl;

    public String createPaymentUrl(OrderResponse response, String transactionId, String ipAddress) {
        long amount = response.getTotalPrice() * 100L;
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", transactionId);
        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang: " + response.getOrderId());
        vnpParams.put("vnp_OrderType", "billpayment");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr",ipAddress);
        vnpParams.put("vnp_CreateDate", LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        vnpParams.put("vnp_BankCode", "VNBANK");
        String queryUrl = getSignedUrl(vnpParams);
        return vnp_PayUrl + "?" + queryUrl;
    }

    private String getSignedUrl(Map<String, String> params) {
        StringBuilder query = new StringBuilder();

        // Tạo chuỗi truy vấn đã mã hóa URL
        for (Map.Entry<String, String> entry : params.entrySet()) {
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .append('&');
        }
        query.setLength(query.length() - 1);

        // Tính chữ ký từ chuỗi đã mã hóa
        String hashData = query.toString();
        String secureHash = hmacSHA512(vnp_HashSecret, hashData);
        query.append("&vnp_SecureHash=").append(secureHash);
        return query.toString();
    }

    public boolean verifyVnpayResponse(Map<String, String> originalParams) {
        String receivedHash = originalParams.get("vnp_SecureHash");

        // Lọc các tham số, bỏ qua vnp_SecureHash và vnp_SecureHashType
        Map<String, String> filtered = new TreeMap<>();
        for (Map.Entry<String, String> entry : originalParams.entrySet()) {
            String key = entry.getKey();
            if (!"vnp_SecureHash".equals(key) && !"vnp_SecureHashType".equals(key)) {
                filtered.put(key, entry.getValue());
            }
        }

        // Tạo chuỗi truy vấn đã mã hóa URL
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : filtered.entrySet()) {
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .append('&');
        }
        query.setLength(query.length() - 1);

        // Tính chữ ký từ chuỗi đã mã hóa
        String calculatedHash = hmacSHA512(vnp_HashSecret, query.toString());

        // Debug
        System.out.println("🔐 HashData:       " + query);
        System.out.println("🔐 CalculatedHash: " + calculatedHash);
        System.out.println("🔐 ReceivedHash:   " + receivedHash);

        return calculatedHash.equalsIgnoreCase(receivedHash);
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] result = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(result);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo HMAC SHA512", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}



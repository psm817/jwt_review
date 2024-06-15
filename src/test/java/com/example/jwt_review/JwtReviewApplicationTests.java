package com.example.jwt_review;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtReviewApplicationTests {

	@Autowired
	private JwtProvider jwtProvider;

	@Value("${custom.jwt.secretKey}")
	private String secretKeyPlain;

	@Test
	@DisplayName("시크릿 키 존재 여부 체크")
	void test1() {
		assertThat(secretKeyPlain).isNotNull();
	}

	@Test
	@DisplayName("시크릿 키를 이용하여 암호화 알고리즘 SecretKey 객체 만들기")
	void test2() {
		// 64비트로 인코딩
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());

		// HMAC 키로 암호화 객체 생성
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());

		assertThat(secretKey).isNotNull();
	}

	@Test
	@DisplayName("JwtProvider 객체를 활용하여 SecretKey 객체 생성")
	void test3() {
		SecretKey secretKey = jwtProvider.getSecretKey();
		assertThat(secretKey).isNotNull();

		System.out.println(secretKey);
	}

	@Test
	@DisplayName("SecretKey 객체 생성을 한 번만 하도록 처리")
	void test4() {
		SecretKey secretKey1 = jwtProvider.getSecretKey();
		SecretKey secretKey2 = jwtProvider.getSecretKey();

		assertThat(secretKey1 == secretKey2).isTrue();
	}

	@Test
	@DisplayName("access Token 발급")
	void test5() {
		Map<String, Object> claims = new HashMap<>();		// claims는 Token에 payload에 저장되는 정보

		claims.put("id", 2L);
		claims.put("username", "psm817");

		String accessToken = jwtProvider.genToken(claims, 60 * 60 * 5);

		System.out.println("accessToken : " + accessToken);

		assertThat(accessToken).isNotNull();
	}
}

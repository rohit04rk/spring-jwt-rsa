package com.mb.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import com.mb.common.constant.JwtConstant;
import com.nimbusds.jose.jwk.RSAKey;

@Configuration
public class JwtStoreConfig {

	@Bean
	RSAKey jwkSource(Environment env) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				keyBytesFromFile(env.getProperty(JwtConstant.RSA_PRIVATE_KEY)));
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				keyBytesFromFile(env.getProperty(JwtConstant.RSA_PUBLIC_KEY)));

		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

		return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
	}

	byte[] keyBytesFromFile(String fileName) throws IOException {

		return Files.readAllBytes(new ClassPathResource(fileName).getFile().toPath());
	}
}

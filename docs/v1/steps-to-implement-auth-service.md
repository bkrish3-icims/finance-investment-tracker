For this setup you need three pieces: an Auth service that issues JWTs, a Gateway that validates them and enriches headers, and a Finance service that trusts JWT and enforces business rules.[1][2]

## 1. Auth service (JWT issuer)

At a high level your Auth service will:

- Expose endpoints such as:
  - `POST /auth/register` → create user in Auth DB.
  - `POST /auth/login` → verify credentials and return `{ accessToken, expiresIn }` (and optionally `refreshToken`).[3][4]
- On successful login, generate a signed JWT that contains at least:
  - `sub` = user id or username,
  - `roles`/`authorities`,
  - `iat` and `exp` (short‑lived, for example 15–30 minutes).[5][2]

Conceptual Spring Boot pieces:

- Entities: `User`, `Role` with JPA; `UserRepository`.
- Security: `PasswordEncoder` (BCrypt), `AuthenticationManager`, and a service method that authenticates and then calls a `JwtService` to generate the token.[4][6]
- `JwtService` uses a secret or keypair and a library like `io.jsonwebtoken` or `NimbusJwtEncoder` to build the token.[7][8]

The desktop/Web UI calls `/auth/login`, stores the token, and sends it as `Authorization: Bearer <token>` to the Gateway on each request.

## 2. Gateway-service: validate + add headers

Your Spring Cloud Gateway will sit in front of Finance and Auth:

- Configure routes so `/auth/**` goes directly to Auth, and `/finance/**` goes to Finance. Auth routes are public; Finance routes use a JWT filter.[9][10]
- Implement a custom `GatewayFilter` (or `AbstractGatewayFilterFactory`) that:
  - Extracts the JWT from `Authorization` header.
  - Validates it (signature, expiration, issuer).
  - On success, reads claims (`sub`, `roles`) and adds headers like `X-User-Id`, `X-User-Roles` onto the request before it is routed to Finance.[11][12]

High‑level flow in the filter:

1. If path is public (e.g. `/auth/**`), skip validation.
2. If no/invalid token → return `401/403`.
3. If valid → `exchange.getRequest().mutate().header("X-User-Id", sub)...` and call `chain.filter` with the mutated request.[9][11]

You can either:

- Forward the original `Authorization` header + extra headers, or
- Strip `Authorization` and rely purely on internal headers; the more common approach is to keep the JWT so downstream services can validate independently.[13][1]

## 3. Finance service: trust JWT and enforce rules

The Finance service should still be secure:

- Configure it as a Spring Security OAuth2 **resource server** so it can validate the JWT using the same issuer / key material that the Auth service uses (for example via `spring.security.oauth2.resourceserver.jwt.jwk-set-uri` or `public-key-location`).[14][15]
- Protect all business endpoints so only authenticated users can access them (`anyRequest().authenticated()`), and then use:
  - Method annotations like `@PreAuthorize("hasRole('USER')")`, and
  - The `Authentication` object (or `Jwt`) to ensure users only access their own data, for example by comparing `sub` with the `userId` in path/DB.[5][14]

With this:

- Auth service issues and signs tokens.
- Gateway validates tokens and normalizes user headers.
- Finance service re‑validates or at least uses the JWT claims/headers to enforce per‑user authorization.

If you want, the next step can be a concrete minimal code skeleton (package names + classes) for all three services tailored to your tech stack.

[1](https://dzone.com/articles/using-jwt-in-a-microservice-architecture)
[2](https://dev.to/ayshriv/securing-microservices-with-spring-security-implementing-jwt-38m6)
[3](https://www.geeksforgeeks.org/springboot/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/)
[4](https://www.bezkoder.com/spring-boot-jwt-authentication/)
[5](https://microservices.io/post/architecture/2025/07/22/microservices-authn-authz-part-3-jwt-authorization.html)
[6](https://www.toptal.com/spring/spring-security-tutorial)
[7](https://stackoverflow.com/questions/77353001/spring-boot-3-jwt-token-generation)
[8](https://www.javainuse.com/spring/boot-jwt)
[9](https://oril.co/blog/spring-cloud-gateway-security-with-jwt/)
[10](https://blog.nashtechglobal.com/spring-cloud-gateway-with-jwt/)
[11](https://www.baeldung.com/spring-cloud-custom-gateway-filters)
[12](https://sergiolema.dev/2021/10/06/how-to-use-the-spring-cloud-gateway-filter-for-authentication/)
[13](https://spring.io/blog/2019/08/16/securing-services-with-spring-cloud-gateway)
[14](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
[15](https://stackoverflow.com/questions/76923238/spring-security-oauth-settings-for-local-jwt-validation)
[16](https://www.reddit.com/r/SpringBoot/comments/1e9chh1/how_to_user_auth_using_jwt_in_spring_boot/)
[17](https://www.youtube.com/watch?v=7fAB4WS29oM)
[18](https://www.youtube.com/watch?v=KxqlJblhzfI)
[19](https://www.reddit.com/r/SpringBoot/comments/zyrna4/how_would_i_configure_spring_security_to_return_a/)
[20](https://www.xoriant.com/blog/microservices-security-using-jwt-authentication-gateway)
[21](https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication)
[22](https://www.youtube.com/watch?v=e2xut5xpmmk)
[23](https://stackoverflow.com/questions/54909509/accessing-jwt-token-from-a-spring-boot-rest-controller)
[24](https://openliberty.io/guides/microprofile-jwt.html)
[25](https://www.youtube.com/watch?v=mSk0_2EWo_s)
[26](https://github.com/maxwellkimaiyo/SpringBoot-Security-JWT)
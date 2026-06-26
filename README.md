# backend

## Environment variables

Required for deployment:

- `JWT_SECRET`: JWT HS256 signing secret. Use a random value of at least 32 bytes.
- `DB_PASSWORD`: MySQL password.

Optional:

- `JWT_ACCESS_TOKEN_EXPIRATION_SECONDS`: access token lifetime in seconds. Default: `3600`.
- `DB_USERNAME`: MySQL username. Default: `root`.
- `CORS_ALLOWED_ORIGINS`: comma-separated allowed origins. Default: `http://localhost:3000,http://localhost:5173`.
- `DEMODAY_TOTAL_VOTER_COUNT`: total voter count for demoday vote progress. Default: `0`.

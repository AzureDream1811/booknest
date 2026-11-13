-- password_hash for 'password' using bcrypt

INSERT INTO users (email, password_hash, display_name, status)
VALUES
    ('alice@example.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'Alice Reader',
     'ACTIVE'),
    ('bob@example.com',
     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
     'Bob Reader',
     'ACTIVE')
ON CONFLICT (email) DO NOTHING;

INSERT INTO refresh_tokens (
    user_id,
    token_hash,
    expires_at
)
SELECT id,
       token_hash,
       CURRENT_TIMESTAMP + INTERVAL '7 days'
FROM (
    SELECT
        (SELECT id FROM users WHERE email = 'alice@example.com') AS id,
        'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa' AS token_hash
    UNION ALL
    SELECT
        (SELECT id FROM users WHERE email = 'bob@example.com') AS id,
        'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb' AS token_hash
) AS mock_refresh_tokens
WHERE id IS NOT NULL
ON CONFLICT (token_hash) DO NOTHING;

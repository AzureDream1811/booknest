ALTER TABLE refresh_tokens
    ADD CONSTRAINT chk_refresh_tokens_expiry_after_created
        CHECK (expires_at > created_at),
    ADD CONSTRAINT chk_refresh_tokens_replacement_requires_revoke
        CHECK (replaced_by_token_hash IS NULL OR revoked_at IS NOT NULL);

-- Test users
INSERT INTO users (email, password_hash, display_name, status)
VALUES
('alice@test.com', 'test_hash', 'Alice', 'ACTIVE'),
('bob@test.com', 'test_hash', 'Bob', 'ACTIVE');

-- Test books
INSERT INTO books (external_id, title, authors, page_count, language)
VALUES
('test-book-001', 'Clean Code', '["Robert C. Martin"]', 464, 'en'),
('test-book-002', 'Effective Java', '["Joshua Bloch"]', 416, 'en'),
('test-book-003', 'The Pragmatic Programmer', '["David Thomas", "Andrew Hunt"]', 352, 'en');
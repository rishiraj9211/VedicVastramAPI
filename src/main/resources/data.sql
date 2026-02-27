INSERT INTO users (id, name, email, password, role, status, created_at)
VALUES (1, 'Admin', 'admin@gmail.com', '$2a$10$VcP8t/HWQ71SlQNQe0uIAuXu.Y6vUJ.e0VUP1fgKX6/i/yVVfCnmS', 'ADMIN', 'APPROVED', CURRENT_TIMESTAMP);

INSERT INTO users (id, name, email, password, role, status, created_at)
VALUES (2, 'Seller1', 'seller@gmail.com', '$2a$10$VcP8t/HWQ71SlQNQe0uIAuXu.Y6vUJ.e0VUP1fgKX6/i/yVVfCnmS', 'SELLER', 'PENDING', CURRENT_TIMESTAMP);

INSERT INTO users (id, name, email, password, role, status, created_at)
VALUES (3, 'Buyer1', 'buyer@gmail.com', '$2a$10$VcP8t/HWQ71SlQNQe0uIAuXu.Y6vUJ.e0VUP1fgKX6/i/yVVfCnmS', 'BUYER', 'APPROVED', CURRENT_TIMESTAMP);

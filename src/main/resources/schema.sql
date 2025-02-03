CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      image_url VARCHAR(255),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tag (
                     id SERIAL PRIMARY KEY,
                     name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE post_tag (
                          post_id INT REFERENCES post(id) ON DELETE CASCADE,
                          tag_id INT REFERENCES tag(id) ON DELETE CASCADE,
                          PRIMARY KEY (post_id, tag_id)
);

CREATE TABLE comment (
                         id SERIAL PRIMARY KEY,
                         post_id INT REFERENCES post(id) ON DELETE CASCADE,
                         content TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE post_like (
                           post_id INT PRIMARY KEY REFERENCES post(id) ON DELETE CASCADE,
                           like_count INT DEFAULT 0
);

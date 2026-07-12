-- drop table  contacts
-- select * from contacts
-- CREATE TABLE contacts (
--                           id SERIAL PRIMARY KEY,
--                           name VARCHAR(100) NOT NULL,
--                           phone_number VARCHAR(20) NOT NULL,
--                           email VARCHAR(100),
--                           category VARCHAR(50),
--                           address VARCHAR(255),
--                           company VARCHAR(100),
--                           notes TEXT,
--                           favorite BOOLEAN DEFAULT FALSE,
--                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );
--
--
-- INSERT INTO contacts (
--     name,
--     phone_number,
--     email,
--     category,
--     address,
--     company,
--     notes,
--     favorite
-- )
-- VALUES
--     ('Aarav Patel', '+91 9876543210', 'aarav.patel@gmail.com', 'Friend',
--      'Surat, Gujarat', 'Patel Technologies', 'School friend', TRUE),
--
--     ('Priya Sharma', '+91 9123456780', 'priya.sharma@gmail.com', 'Work',
--      'Mumbai, Maharashtra', 'Infosys', 'Project coordinator', FALSE),
--
--     ('Rohan Mehta', '+91 9988776655', 'rohan.mehta@yahoo.com', 'Family',
--      'Ahmedabad, Gujarat', NULL, 'Cousin', TRUE),
--
--     ('Neha Verma', '+91 9012345678', 'neha.verma@outlook.com', 'Friend',
--      'Pune, Maharashtra', 'TCS', 'College friend', FALSE),
--
--     ('Vikram Singh', '+91 9871203456', 'vikram.singh@gmail.com', 'Business',
--      'Delhi, India', 'Singh Enterprises', 'Client', FALSE),
--
--     ('Ananya Desai', '+91 9988123456', 'ananya.desai@gmail.com', 'Family',
--      'Vadodara, Gujarat', NULL, 'Sister', TRUE),
--
--     ('Rahul Joshi', '+91 9123987654', 'rahul.joshi@gmail.com', 'Work',
--      'Bangalore, Karnataka', 'Wipro', 'Backend developer', FALSE),
--
--     ('Sneha Kapoor', '+91 9000112233', 'sneha.kapoor@gmail.com', 'Friend',
--      'Jaipur, Rajasthan', 'Freelancer', 'Travel buddy', FALSE),
--
--     ('Karan Shah', '+91 9111223344', 'karan.shah@gmail.com', 'Business',
--      'Hyderabad, Telangana', 'Shah Solutions', 'Vendor contact', FALSE),
--
--     ('Dr. Meera Nair', '+91 9222334455', 'meera.nair@hospital.com', 'Emergency',
--      'Kochi, Kerala', 'City Hospital', 'Family doctor', TRUE),
--
--     ('Arjun Kumar', '+91 9333444555', 'arjun.kumar@gmail.com', 'Work',
--      'Chennai, Tamil Nadu', 'Zoho', 'Team lead', FALSE),
--
--     ('Pooja Bhatt', '+91 9444555666', 'pooja.bhatt@gmail.com', 'Friend',
--      'Indore, Madhya Pradesh', NULL, 'Gym friend', FALSE),
--
--     ('Sanjay Gupta', '+91 9555666777', 'sanjay.gupta@gmail.com', 'Business',
--      'Lucknow, Uttar Pradesh', 'Gupta Traders', 'Supplier', FALSE),
--
--     ('Ishita Roy', '+91 9666777888', 'ishita.roy@gmail.com', 'Family',
--      'Kolkata, West Bengal', NULL, 'Aunt', TRUE),
--
--     ('Aditya Malhotra', '+91 9777888999', 'aditya.m@gmail.com', 'Friend',
--      'Chandigarh', 'Accenture', 'Former colleague', FALSE);

CREATE TABLE call_history (
                              id SERIAL PRIMARY KEY,
                              contact_id INT,
                              name VARCHAR(100),
                              phone_number VARCHAR(20) NOT NULL,
                              call_status VARCHAR(20) NOT NULL,
                              duration_seconds INT DEFAULT 0,
                              started_at TIMESTAMP NOT NULL,
                              ended_at TIMESTAMP,

                              CONSTRAINT fk_contact
                                  FOREIGN KEY (contact_id)
                                      REFERENCES contacts(id)
                                      ON DELETE SET NULL
);

INSERT INTO call_history (
    contact_id,
    name,
    phone_number,
    call_status,
    duration_seconds,
    started_at,
    ended_at
)
SELECT
    c.id,
    c.name,
    v.phone_number,
    v.call_status,
    v.duration_seconds,
    v.started_at,
    v.ended_at
FROM (
         VALUES
             ('+91 9876543210', 'Answered', 320, '2026-07-05 09:15:00', '2026-07-05 09:20:20'),
             ('+91 9123456780', 'Answered', 780, '2026-07-05 10:30:00', '2026-07-05 10:43:00'),
             ('+91 9988776655', 'Missed', 0, '2026-07-05 11:10:00', NULL),
             ('+91 9012345678', 'Rejected', 0, '2026-07-05 12:15:00', NULL),
             ('+91 9871203456', 'Answered', 95, '2026-07-05 13:40:00', '2026-07-05 13:41:35'),
             ('+91 9000000000', 'Missed', 0, '2026-07-05 14:00:00', NULL) -- unknown number example
     ) v(phone_number, call_status, duration_seconds, started_at, ended_at)
         LEFT JOIN contacts c
                   ON c.phone_number = v.phone_number;
-- contacts query for history


-- SELECT *
-- FROM call_history
-- ORDER BY started_at DESC;


CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       mobile VARCHAR(15) UNIQUE NOT NULL,
                       upi_id VARCHAR(50) UNIQUE,
                       pin VARCHAR(10) NOT NULL,
                       balance DOUBLE PRECISION DEFAULT 0,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       blocked BOOLEAN DEFAULT FALSE
);

CREATE TABLE transactions (
                              txn_id SERIAL PRIMARY KEY,

                              from_user_id INT,
                              to_user_id INT,

                              from_upi VARCHAR(50),
                              to_upi VARCHAR(50),

                              amount DOUBLE PRECISION NOT NULL,

                              txn_type VARCHAR(30),   -- UPI, Recharge, Bill, FASTAG etc
                              status VARCHAR(20),     -- SUCCESS, FAILED

                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              FOREIGN KEY (from_user_id) REFERENCES users(user_id),
                              FOREIGN KEY (to_user_id) REFERENCES users(user_id)
);
CREATE TABLE messages(
                        id integer PRIMARY KEY,
                        name varchar(200),
                        extra timestamp,
                        message varchar(1000),
                        c_id int,
                        FOREIGN KEY (c_id)
                        REFERENCES contacts(id)
);
--NAME OF FORIGN KEY IS m_foreign_key
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
    v.started_at::Timestamp,
    v.ended_at::Timestamp
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


-- trigger -
CREATE OR REPLACE FUNCTION calculate_call_duration()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.started_at IS NOT NULL AND NEW.ended_at IS NOT NULL THEN
        NEW.duration_seconds :=
            EXTRACT(EPOCH FROM (NEW.ended_at - NEW.started_at))::INT;
ELSE
        NEW.duration_seconds := 0;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_calculate_call_duration
    BEFORE INSERT OR UPDATE
                         ON call_history
                         FOR EACH ROW
                         EXECUTE FUNCTION calculate_call_duration();
--cc trigger +91
CREATE OR REPLACE FUNCTION add_default_country_code()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.phone_number IS NOT NULL
       AND NEW.phone_number NOT LIKE '+%' THEN
        NEW.phone_number := '+91 ' || TRIM(NEW.phone_number);
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_add_country_code
    BEFORE INSERT OR UPDATE OF phone_number
                     ON call_history
                         FOR EACH ROW
                         EXECUTE FUNCTION add_default_country_code();



-- contacts query for history


-- SELECT *
-- FROM call_history
-- ORDER BY started_at DESC;


CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       mobile VARCHAR(15) UNIQUE NOT NULL,
                       upi_id VARCHAR(50) UNIQUE,
                       pin VARCHAR(4) NOT NULL,
                       balance DOUBLE PRECISION DEFAULT 10000,
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

                              status VARCHAR(20),     -- SUCCESS, FAILED

                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              FOREIGN KEY (from_user_id) REFERENCES users(user_id),
                              FOREIGN KEY (to_user_id) REFERENCES users(user_id)
);


CREATE OR REPLACE FUNCTION auto_create_contact()
RETURNS TRIGGER AS
$$
DECLARE
new_contact_id INT;
BEGIN

    -- Incoming unknown sender
    IF NEW.from_id IS NULL AND NEW.from_name IS NOT NULL THEN

        INSERT INTO contacts
        (
            name,
            phone_number,
            category,
            favorite
        )
        VALUES
        (
            NEW.from_name,
            'Unknown',
            'Unsaved',
            FALSE
        )
        RETURNING id INTO new_contact_id;

        NEW.from_id := new_contact_id;

END IF;

    -- Unknown receiver
    IF NEW.to_id IS NULL AND NEW.to_name IS NOT NULL THEN

        INSERT INTO contacts
        (
            name,
            phone_number,
            category,
            favorite
        )
        VALUES
        (
            NEW.to_name,
            'Unknown',
            'Unsaved',
            FALSE
        )
        RETURNING id INTO new_contact_id;

        NEW.to_id := new_contact_id;

END IF;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;
----
CREATE TRIGGER trg_auto_create_contact
    BEFORE INSERT
    ON chat_messages
    FOR EACH ROW
    EXECUTE FUNCTION auto_create_contact();
--------------------------------------------------



CREATE OR REPLACE FUNCTION create_message_thread()
RETURNS TRIGGER AS
$$
BEGIN

INSERT INTO message_threads
(
    contact_id,
    name,
    last_message,
    updated_at
)
SELECT
    NEW.to_id,
    NEW.to_name,
    NEW.message,
    NEW.sent_at
    WHERE NOT EXISTS
    (
        SELECT 1
        FROM message_threads
        WHERE contact_id = NEW.to_id
    );

UPDATE message_threads
SET
    last_message = NEW.message,
    updated_at = NEW.sent_at
WHERE contact_id = NEW.to_id;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;
----


CREATE TRIGGER trg_create_thread
    AFTER INSERT
    ON chat_messages
    FOR EACH ROW
    EXECUTE FUNCTION create_message_thread();
--------------------------------------------------





INSERT INTO message (msg_id, contact_id, name, last_message, updated_at) VALUES
                                                                             (1,1,'Aarav Patel','Reached home safely!','2026-07-11 21:18:32'),
                                                                             (2,2,'Priya Sharma','Please send the report before 5 PM.','2026-07-11 16:41:12'),
                                                                             (3,3,'Rohan Mehta','Dinner tonight?','2026-07-10 20:15:41'),
                                                                             (4,4,'Neha Verma','😂😂😂','2026-07-09 22:44:12'),
                                                                             (5,5,'Vikram Singh','Invoice received.','2026-07-08 14:11:52'),
                                                                             (6,6,'Ananya Desai','Love you bhai ❤️','2026-07-11 23:04:12'),
                                                                             (7,7,'Rahul Joshi','Lets deploy tomorrow.','2026-07-10 18:33:11'),
                                                                             (8,8,'Sneha Kapoor','Trip confirmed!','2026-07-08 11:10:53'),
                                                                             (9,9,'Karan Shah','Meeting rescheduled.','2026-07-07 17:20:42'),
                                                                             (10,10,'Dr. Meera Nair','Take medicines after food.','2026-07-06 09:18:00'),
                                                                             (11,11,'Arjun Kumar','Joining in 5 mins.','2026-07-11 10:15:12'),
                                                                             (12,12,'Pooja Bhatt','Gym tomorrow?','2026-07-09 19:42:22'),
                                                                             (13,13,'Sanjay Gupta','Stock dispatched.','2026-07-08 15:22:10'),
                                                                             (14,14,'Ishita Roy','Happy Birthday! ❤️','2026-07-05 08:14:52'),
                                                                             (15,15,'Aditya Malhotra','Long time no see!','2026-07-11 13:12:51'),
                                                                             (16,16,'cvbnm','Hello','2026-07-02 16:51:20'),
                                                                             (17,17,'gcfvbnm vvcx','Okay','2026-07-01 11:12:00'),
                                                                             (18,18,'jhgf gtfd','See you.','2026-07-04 15:22:10'),
                                                                             (19,25,'udit narayan','Done 👍','2026-07-11 18:31:10'),
                                                                             (20,26,'chintan','Meet at college tomorrow.','2026-07-11 20:00:00'),
                                                                             (21,31,'dhruvi','Good night 😊','2026-07-11 23:40:22'),
                                                                             (22,NULL,'Unknown +91 8899776655','Who is this?','2026-07-09 12:12:12'),
                                                                             (23,NULL,'Unknown +91 9765432101','Call me back.','2026-07-10 17:20:12'),
                                                                             (24,NULL,'Unknown +91 9011122233','Package delivered.','2026-07-08 15:30:45'),
                                                                             (25,NULL,'Unknown +91 9321456789','OTP: 493822','2026-07-06 09:12:01');













INSERT INTO chat_messages
(c_id,from_id,to_id,from_name,to_name,message,sent_at)
VALUES

    (1,1,0,'Aarav Patel','Me','Hey!','2026-07-10 08:10:21'),
    (2,0,1,'Me','Aarav Patel','Morning!','2026-07-10 08:11:11'),
    (3,1,0,'Aarav Patel','Me','Coming to college?','2026-07-10 08:11:54'),
    (4,0,1,'Me','Aarav Patel','Yes, leaving now.','2026-07-10 08:13:22'),
    (5,1,0,'Aarav Patel','Me','Cool 👍','2026-07-10 08:13:45'),

    (6,2,0,'Priya Sharma','Me','Need the presentation.','2026-07-09 09:01:12'),
    (7,0,2,'Me','Priya Sharma','I"ll send it in 10 minutes.','2026-07-09 09:05:32'),
    (8,2,0,'Priya Sharma','Me','Thanks!','2026-07-09 09:06:41'),
    (9,0,2,'Me','Priya Sharma','Shared on email.','2026-07-09 09:09:01'),
    (10,2,0,'Priya Sharma','Me','Received.','2026-07-09 09:10:18'),

    (11,3,0,'Rohan Mehta','Me','Cricket today?','2026-07-08 18:12:55'),
    (12,0,3,'Me','Rohan Mehta','Definitely!','2026-07-08 18:15:31'),
    (13,3,0,'Rohan Mehta','Me','6 PM ground.','2026-07-08 18:16:41'),
    (14,0,3,'Me','Rohan Mehta','See you.','2026-07-08 18:17:22'),

    (15,4,0,'Neha Verma','Me','😂😂😂','2026-07-08 22:01:41'),
    (16,0,4,'Me','Neha Verma','That meme was hilarious.','2026-07-08 22:02:10'),
    (17,4,0,'Neha Verma','Me','I know 😂','2026-07-08 22:03:18'),

    (18,5,0,'Vikram Singh','Me','Invoice sent.','2026-07-07 14:02:01'),
    (19,0,5,'Me','Vikram Singh','Received.','2026-07-07 14:04:55'),
    (20,5,0,'Vikram Singh','Me','Payment by Monday.','2026-07-07 14:05:42'),

    (21,6,0,'Ananya Desai','Me','Happy Raksha Bandhan ❤️','2026-07-06 08:11:10'),
    (22,0,6,'Me','Ananya Desai','Thank you ❤️','2026-07-06 08:12:32'),
    (23,6,0,'Ananya Desai','Me','Come home early.','2026-07-06 08:14:11'),

    (24,7,0,'Rahul Joshi','Me','Deployment completed.','2026-07-05 17:42:20'),
    (25,0,7,'Me','Rahul Joshi','Any production issues?','2026-07-05 17:44:11'),
    (26,7,0,'Rahul Joshi','Me','None till now.','2026-07-05 17:46:03'),

    (27,8,0,'Sneha Kapoor','Me','Tickets booked!','2026-07-04 11:20:01'),
    (28,0,8,'Me','Sneha Kapoor','Awesome 😍','2026-07-04 11:21:41'),
    (29,8,0,'Sneha Kapoor','Me','Packing starts tomorrow.','2026-07-04 11:23:52'),

    (30,9,0,'Karan Shah','Me','Meeting shifted to Friday.','2026-07-03 16:10:01'),
    (31,0,9,'Me','Karan Shah','Works for me.','2026-07-03 16:12:42'),
    (32,9,0,'Karan Shah','Me','See you then.','2026-07-03 16:13:12'),

    (33,10,0,'Dr. Meera Nair','Me','How are you feeling?','2026-07-02 09:20:02'),
    (34,0,10,'Me','Dr. Meera Nair','Much better.','2026-07-02 09:21:44'),
    (35,10,0,'Dr. Meera Nair','Me','Continue medicines.','2026-07-02 09:22:52'),

    (36,NULL,0,'Unknown +91 8899776655','Me','Hi','2026-07-01 12:20:20'),
    (37,0,NULL,'Me','Unknown +91 8899776655','Who is this?','2026-07-01 12:21:31'),
    (38,NULL,0,'Unknown +91 8899776655','Me','Wrong number sorry.','2026-07-01 12:22:12'),

    (39,NULL,0,'Unknown +91 9765432101','Me','Call me when free.','2026-07-01 19:01:10'),
    (40,0,NULL,'Me','Unknown +91 9765432101','Who"s this?','2026-07-01 19:02:51'),
    (41,11,0,'Arjun Kumar','Me','Are you joining the meeting?','2026-06-30 10:05:12'),
(42,0,11,'Me','Arjun Kumar','Yes, joining now.','2026-06-30 10:06:02'),
(43,11,0,'Arjun Kumar','Me','Share your updates first.','2026-06-30 10:08:11'),
(44,0,11,'Me','Arjun Kumar','API module completed.','2026-06-30 10:10:22'),
(45,11,0,'Arjun Kumar','Me','Great work 👍','2026-06-30 10:11:01'),

(46,12,0,'Pooja Bhatt','Me','Gym today?','2026-06-29 17:20:11'),
(47,0,12,'Me','Pooja Bhatt','After 7 PM.','2026-06-29 17:21:02'),
(48,12,0,'Pooja Bhatt','Me','Okay see you there.','2026-06-29 17:22:45'),
(49,0,12,'Me','Pooja Bhatt','Don"t forget water bottle 😂','2026-06-29 17:23:18'),
(50,12,0,'Pooja Bhatt','Me','Haha okay.','2026-06-29 17:24:01'),

(51,13,0,'Sanjay Gupta','Me','Shipment reached warehouse.','2026-06-28 13:15:32'),
(52,0,13,'Me','Sanjay Gupta','Thanks for the update.','2026-06-28 13:17:10'),
(53,13,0,'Sanjay Gupta','Me','Delivery tomorrow morning.','2026-06-28 13:18:41'),
(54,0,13,'Me','Sanjay Gupta','Noted.','2026-06-28 13:19:05'),

(55,14,0,'Ishita Roy','Me','Happy birthday 🎂','2026-06-27 08:01:12'),
(56,0,14,'Me','Ishita Roy','Thank you ❤️','2026-06-27 08:03:10'),
(57,14,0,'Ishita Roy','Me','Party when?','2026-06-27 08:04:20'),
(58,0,14,'Me','Ishita Roy','Weekend maybe.','2026-06-27 08:05:41'),

(59,15,0,'Aditya Malhotra','Me','Long time no talk.','2026-06-26 20:11:02'),
(60,0,15,'Me','Aditya Malhotra','Yeah bro, how are you?','2026-06-26 20:12:21'),
(61,15,0,'Aditya Malhotra','Me','Doing good. New job is busy.','2026-06-26 20:14:44'),
(62,0,15,'Me','Aditya Malhotra','Congratulations 🎉','2026-06-26 20:15:12'),

(63,25,0,'udit narayan','Me','Assignment completed.','2026-06-25 15:30:21'),
(64,0,25,'Me','udit narayan','Send me the file.','2026-06-25 15:31:05'),
(65,25,0,'udit narayan','Me','Uploaded.','2026-06-25 15:33:41'),

(66,26,0,'chintan','Me','College tomorrow?','2026-06-24 21:10:11'),
(67,0,26,'Me','chintan','Yes at 9.','2026-06-24 21:11:30'),
(68,26,0,'chintan','Me','Meet near canteen.','2026-06-24 21:12:04'),
(69,0,26,'Me','chintan','Done 👍','2026-06-24 21:13:18'),

(70,31,0,'dhruvi','Me','Good morning 😊','2026-06-23 07:20:10'),
(71,0,31,'Me','dhruvi','Good morning.','2026-06-23 07:21:02'),
(72,31,0,'dhruvi','Me','Reached class.','2026-06-23 08:00:55'),
(73,0,31,'Me','dhruvi','Okay.','2026-06-23 08:01:20'),

(74,NULL,0,'Unknown +91 9988112233','Me','Hello sir','2026-06-22 11:11:12'),
(75,0,NULL,'Me','Unknown +91 9988112233','Who is this?','2026-06-22 11:12:33'),
(76,NULL,0,'Unknown +91 9988112233','Me','From courier service.','2026-06-22 11:13:42'),

(77,NULL,0,'Unknown +91 9898989898','Me','Can you send notes?','2026-06-21 16:22:12'),
(78,0,NULL,'Me','Unknown +91 9898989898','Sure, wait.','2026-06-21 16:23:44'),
(79,NULL,0,'Unknown +91 9898989898','Me','Thanks.','2026-06-21 16:30:01'),

(80,NULL,0,'Unknown +91 9345678123','Me','Are you available?','2026-06-20 19:12:10'),
(81,0,NULL,'Me','Unknown +91 9345678123','After dinner.','2026-06-20 19:13:22'),

(82,2,0,'Priya Sharma','Me','Reminder for tomorrow meeting.','2026-06-19 14:12:11'),
(83,0,2,'Me','Priya Sharma','Added to calendar.','2026-06-19 14:14:51'),
(84,2,0,'Priya Sharma','Me','Perfect.','2026-06-19 14:15:30'),

(85,3,0,'Rohan Mehta','Me','Movie tonight?','2026-06-18 18:40:10'),
(86,0,3,'Me','Rohan Mehta','Which one?','2026-06-18 18:41:11'),
(87,3,0,'Rohan Mehta','Me','New action movie.','2026-06-18 18:42:03'),
(88,0,3,'Me','Rohan Mehta','Let"s go.','2026-06-18 18:42:44'),

(89,4,0,'Neha Verma','Me','Check this photo 😂','2026-06-17 22:11:01'),
(90,0,4,'Me','Neha Verma','Amazing 😂','2026-06-17 22:12:14'),

(91,7,0,'Rahul Joshi','Me','Server restarted.','2026-06-16 09:10:02'),
(92,0,7,'Me','Rahul Joshi','Checking logs.','2026-06-16 09:11:20'),
(93,7,0,'Rahul Joshi','Me','Issue fixed.','2026-06-16 09:15:41'),

(94,9,0,'Karan Shah','Me','Quotation approved.','2026-06-15 12:30:00'),
(95,0,9,'Me','Karan Shah','Starting work today.','2026-06-15 12:32:14'),
(96,9,0,'Karan Shah','Me','Great.','2026-06-15 12:33:01'),

(97,10,0,'Dr. Meera Nair','Me','Appointment tomorrow 5 PM.','2026-06-14 18:20:12'),
(98,0,10,'Me','Dr. Meera Nair','Confirmed.','2026-06-14 18:22:01'),

(99,NULL,NULL,'Unknown +91 8888777766','Unknown +91 7777666655','Hello, are you there?','2026-06-13 13:15:10'),
(100,NULL,NULL,'Unknown +91 7777666655','Unknown +91 8888777766','Yes.','2026-06-13 13:16:42');;
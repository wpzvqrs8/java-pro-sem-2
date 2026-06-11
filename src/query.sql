drop table  contacts
select * from contacts
CREATE TABLE contacts (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          phone_number VARCHAR(20) NOT NULL,
                          email VARCHAR(100),
                          category VARCHAR(50),
                          address VARCHAR(255),
                          company VARCHAR(100),
                          notes TEXT,
                          favorite BOOLEAN DEFAULT FALSE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO contacts (
    name,
    phone_number,
    email,
    category,
    address,
    company,
    notes,
    favorite
)
VALUES
    ('Aarav Patel', '+91 9876543210', 'aarav.patel@gmail.com', 'Friend',
     'Surat, Gujarat', 'Patel Technologies', 'School friend', TRUE),

    ('Priya Sharma', '+91 9123456780', 'priya.sharma@gmail.com', 'Work',
     'Mumbai, Maharashtra', 'Infosys', 'Project coordinator', FALSE),

    ('Rohan Mehta', '+91 9988776655', 'rohan.mehta@yahoo.com', 'Family',
     'Ahmedabad, Gujarat', NULL, 'Cousin', TRUE),

    ('Neha Verma', '+91 9012345678', 'neha.verma@outlook.com', 'Friend',
     'Pune, Maharashtra', 'TCS', 'College friend', FALSE),

    ('Vikram Singh', '+91 9871203456', 'vikram.singh@gmail.com', 'Business',
     'Delhi, India', 'Singh Enterprises', 'Client', FALSE),

    ('Ananya Desai', '+91 9988123456', 'ananya.desai@gmail.com', 'Family',
     'Vadodara, Gujarat', NULL, 'Sister', TRUE),

    ('Rahul Joshi', '+91 9123987654', 'rahul.joshi@gmail.com', 'Work',
     'Bangalore, Karnataka', 'Wipro', 'Backend developer', FALSE),

    ('Sneha Kapoor', '+91 9000112233', 'sneha.kapoor@gmail.com', 'Friend',
     'Jaipur, Rajasthan', 'Freelancer', 'Travel buddy', FALSE),

    ('Karan Shah', '+91 9111223344', 'karan.shah@gmail.com', 'Business',
     'Hyderabad, Telangana', 'Shah Solutions', 'Vendor contact', FALSE),

    ('Dr. Meera Nair', '+91 9222334455', 'meera.nair@hospital.com', 'Emergency',
     'Kochi, Kerala', 'City Hospital', 'Family doctor', TRUE),

    ('Arjun Kumar', '+91 9333444555', 'arjun.kumar@gmail.com', 'Work',
     'Chennai, Tamil Nadu', 'Zoho', 'Team lead', FALSE),

    ('Pooja Bhatt', '+91 9444555666', 'pooja.bhatt@gmail.com', 'Friend',
     'Indore, Madhya Pradesh', NULL, 'Gym friend', FALSE),

    ('Sanjay Gupta', '+91 9555666777', 'sanjay.gupta@gmail.com', 'Business',
     'Lucknow, Uttar Pradesh', 'Gupta Traders', 'Supplier', FALSE),

    ('Ishita Roy', '+91 9666777888', 'ishita.roy@gmail.com', 'Family',
     'Kolkata, West Bengal', NULL, 'Aunt', TRUE),

    ('Aditya Malhotra', '+91 9777888999', 'aditya.m@gmail.com', 'Friend',
     'Chandigarh', 'Accenture', 'Former colleague', FALSE);
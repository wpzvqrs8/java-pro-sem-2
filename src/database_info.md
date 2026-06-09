# contacts table 
- table "calls" = fields -> name / mno / time stamp 
- table "contacts" = fields -> c_id(P.K.)/ full name / mobile no / tag(description)

# payment app
- table "user" = fields -> user_id(P.K.) / name / account opened on (timestamp) /balance
- table "payments" = fields -> user_id(P.K.)/ paid_to user_id / transaction timestamp / amount paid

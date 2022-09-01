ALTER TABLE users ADD COLUMN phone_number varchar(50);
ALTER TABLE users ADD COLUMN verification_status varchar(20) NOT NULL DEFAULT 'VERIFIED';


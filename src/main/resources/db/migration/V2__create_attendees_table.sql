CREATE TABLE attendees (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    report_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (report_id) REFERENCES report(id)
);
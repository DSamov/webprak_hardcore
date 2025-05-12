--DELETE FROM Instances;
INSERT INTO Instances (client_id, employee_id, service_id, start_date, finish_date)
VALUES (1, 1, 1, '2024-06-30', '2024-07-05'),
       (2, 2, 2, '2024-08-13', '2024-08-29'),
       (3, 3, 3, '2025-03-22', NULL),
       (4, 4, 4, '2025-01-02', '2025-03-01'),
       (5, 5,5, '2024-10-09', '2024-11-30');
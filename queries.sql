SELECT name FROM employer Where location = "St. Louis City";

DROP TABLE job;

SELECT * FROM skill INNER JOIN job_skills ON skill.id = job_skills.skills_id
WHERE job_skills.jobs_id IS NOT NULL ORDER BY name ASC;


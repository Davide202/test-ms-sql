DROP PROCEDURE IF EXISTS [dbo].[spGetInfo] ;

CREATE PROCEDURE [dbo].[spGetInfo]

AS

BEGIN
DECLARE  @result TABLE (
        id int ,
        name varchar(255),
        address varchar(255),
        birthday datetime2(6)
)
INSERT INTO @result
    SELECT i.id,i.name,i.address,i.birthday
    FROM information AS i

END

SELECT * FROM @result

GO

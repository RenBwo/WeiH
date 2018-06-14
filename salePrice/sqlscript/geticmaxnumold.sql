

CREATE PROCEDURE GetICMaxNumOld  
    @TableName VARCHAR (50),  
    @FInterID  INT OUTPUT,  
    @Increment INT = 1  
AS  
    BEGIN TRANSACTION  
      
    UPDATE ICMaxNum  
       SET FMaxNum = FMaxNum  
     WHERE FTableName = @TableName  
      
    IF EXISTS ( SELECT FMaxNum  
                  FROM ICMaxNum  
                 WHERE FTableName = @TableName)  
    BEGIN  
        UPDATE ICMaxNum  
           SET FMaxNum = FMaxNum + @Increment  
         WHERE FTableName = @TableName  
          
        SELECT @FInterID = FMaxNum - @Increment + 1  
          FROM ICMaxNum  
         WHERE FTableName = @TableName  
    END  
    ELSE  
    BEGIN  
        INSERT INTO ICMaxNum(FTableName, FMaxNum)  
             VALUES (@TableName, 999 + @Increment)  
        SELECT @FInterID = 1000  
    END  
    IF @@ERROR = 0  
        COMMIT TRANSACTION  
    ELSE  
    BEGIN  
        ROLLBACK TRANSACTION  
        SELECT @FInterID = -1  
    END  
    RETURN @FInterID
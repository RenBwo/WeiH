--<ScriptOptions statementTerminator="GO"/>

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE PROCEDURE GetICMaxNum
    @TableName VARCHAR (50),  
    @FInterID  int output,    
    @Increment INT = 1 ,   
    @UserID    int =0 ---增加用户ID    
as    
    set nocount on  
    declare @FNumber int  
    declare @Sql     nvarchar(4000)  
    declare @i int  
--设置步长
    declare @iStep int
    if @TableName='T_Item'
        select @iStep=1
    else
        select @iStep=2
    select @FinterID=-1 
    --如果用户ID等于0，按照老的存储过程取最大编号（性能优化兼容其它系统）    
    IF  @UserID=0  
    BEGIN  
	 exec GetICMaxNumOld @TableName, @FInterID output,@Increment  
	 return @FInterID  
    END     
    set @FNumber=0  
    set @i=1  
    set @Sql='' 
 
   UPDATE ICMaxNum SET FMaxNum = FMaxNum WHERE FTableName = @TableName 
    select top 1 @FNumber=Isnull(FNumber,0) From  IC_MaxNum where FUserID=@UserID and FFlag=0 and FTableName=@TableName order by FNumber  
  
    --如果为0表示当前表中未分配记录，需要重新分配  
    if @FNumber=0  
    begin  
   
 select @FInterID=isnull(FMaxNum,0)
 from ICMaxNum    
 where FTableName= @TableName    
 if @FInterID >0    
 begin    
  begin transaction    
  --分配号码段  
   set @Sql=@Sql+'Update ICMaxNum set FMaxNum=FMaxNum+'+str(@iStep)+' where FTableName='''+ @TableName+''' '    
  set @Sql=@Sql+' Delete from  IC_MaxNum  where FTableName='''+ @TableName +''' and  FUserID='+str(ltrim(@UserID))+' and FFlag=1'  
                while @i<=@iStep  
                begin  
                   set @Sql=@Sql+' insert into IC_MAxNum(FTableName,FUserID,FNumber) values('''+@TableName+''','  
   +str(ltrim(@UserID))+','+str(@FInterID+@i)+')  '  
                   set @i=@i+1  
                end  
                set @Sql=@Sql+' Update IC_MAxNum set FFlag=1 where FUserID='+str(ltrim(@UserID))+' and FTableName='''+@TableName+''' and FNumber='+str(@FInterID + 1)                   
   exec(@Sql)  
  if @@error=0    
   begin    
       commit    
       select @FInterID=@FInterID + 1    
   end    
  else    
   begin    
      
       rollback    
       select @FInterID=-1    
                
   end    
 end    
 else    
 begin    
  begin transaction    
  if @FinterID=-1 
        set @Sql='Insert Into ICMaxNum(FTableName,FMaxNum) values('''+@tableName+''','+str(1000+@iStep)+') '  
  else
        set  @Sql='update ICMaxNum set FMaxNum='+str(1000+@iStep)+' where FTableName='''+@tableName+''''
  while @i<=@iStep  
	begin  
	   set @Sql=@Sql+' insert into IC_MAxNum(FTableName,FUserID,FNumber) values('''+@TableName+''','  
			+str(ltrim(@UserID))+','+str(1000+@i)+')  '  
	   set @i=@i+1  
	end  
  set @Sql=@Sql+' Update IC_MAxNum set FFlag=1 where FUserID='+str(ltrim(@UserID))+' and FTableName='''+@TableName+''' and FNumber=1001'   
  exec (@Sql)  
  
  if @@error=0    
  begin    
      commit    
      select @FInterID=1001    
  end    
  else    
  begin    
      rollback    
      select @FinterID=-1    
  end       
 end    
    end  
    else   --否则直接获取分配的号码  
    begin  
        begin tran  
            Update IC_MAxNum set FFlag=1 where FUserID=@UserID and FTableName=@TableName and FNumber=@FNumber   
            set @FInterID=@FNumber  
        commit   
    end  
  
    return @FInterID
GO

SET ANSI_PADDING OFF
GO


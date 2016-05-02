selectWithPhoneNumber
===
select * from user where 1=1
@if(!isEmpty(phoneNumber)){
and phoneNumber = #phoneNumber#
@}          


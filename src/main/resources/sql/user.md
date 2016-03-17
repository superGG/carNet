selectWithName
===
select * from user where 1=1
@if(!isEmpty(username)){
and username = #username#
@}          

updatePasswd
===
update user set password = #newPassword# where 1=1
and id = #id#  


findRole
===
select r.* from user u left outer join userrole ur on u.id=ur.userId left outer join role r on ur.roleId=r.id where u.Id = #_root#

findRoleName
===
select r.rolename from user u left outer join userrole ur on u.id=ur.userId left outer join role r on ur.roleId=r.id where u.Id = #_root#

findPrivilegeCode
===
select p.privilegeCode from user u left outer join userrole ur on u.id=ur.userId left outer join role r on ur.roleId=r.id left outer join roleprivilege rp on r.id= rp.roleid left outer join privilege p on rp.privilegeid = p.id where u.Id = #_root#

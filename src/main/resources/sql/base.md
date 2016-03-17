searchSimilarity
===
select * from #text(tableName)# where 1=1
@for(entry in notNullProperties){
@var key = entry.key;
@var value = entry.value;
	and #text(key)# like  #'%'+value+'%'#
@}

searchQuery
===
update * from #text(tableName)# where 1=1

and password = #newPassword# where 1=1

and date < #min# date > #max# 

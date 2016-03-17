package com.earl.carnet.domain.sercurity.roleprivilege;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;

@Table(name = "roleprivilege")
public class RolePrivilege extends AbstractAuditingEntity<Long> implements Serializable {
	
//    private Long id;

    private Long roleId;

    private Long privilegeId;

    private static final long serialVersionUID = 1L;

    @AutoID
    public Long getId() {
        return super.getId();
    }
    
    public void setId(Long id) {
    	super.setId(id);
//        this.id = id;
    }

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Override
	public String toString() {
		return "RolePrivilege [roleId=" + roleId + ", privilegeId=" + privilegeId + "]";
	}
}
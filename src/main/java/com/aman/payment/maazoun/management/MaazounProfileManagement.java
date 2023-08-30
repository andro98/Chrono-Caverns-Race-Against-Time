package com.aman.payment.maazoun.management;

import java.util.List;

import org.springframework.data.domain.Page;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.dto.MaazounProfilePagingDTO;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.AddEditMaazounMaazouniaChurchRequest;
import com.aman.payment.maazoun.model.payload.AddEditMaazounProfileRequest;
import com.aman.payment.maazoun.model.payload.AddEditMaazounSectorRequest;
import com.aman.payment.maazoun.model.payload.DeleteMaazounMaazouniaChurchRequest;
import com.aman.payment.maazoun.model.payload.EditMaazounCloseCustody;
import com.aman.payment.maazoun.model.payload.MaazounByIDRequest;
import com.aman.payment.maazoun.model.payload.MaazounProfileAuditRequest;
import com.aman.payment.maazoun.model.payload.MaazounProfileRequest;
import com.aman.payment.maazoun.model.payload.SearchmaazounProfileRequest;

public interface MaazounProfileManagement {
	
	public List<String> getAllMaazouns();
	
	public MaazounProfilePagingDTO getAllMaazouns(MaazounProfileRequest maazounProfileRequest, CustomUserDetails customUserDetails);
	
	public String addMaazounProfile(AddEditMaazounProfileRequest addEditMaazounProfileRequest, String username);
	
	public String editMaazounProfile(AddEditMaazounProfileRequest addEditMaazounProfileRequest, String username);
	
	public String addEditMaazounSector(AddEditMaazounSectorRequest addEditMaazounSectorRequest);
	
	public String addEditMaazounMaazouniaChurch(AddEditMaazounMaazouniaChurchRequest addEditMaazounMaazouniaChurchRequest);
	
	public String deleteMaazounMaazouniaChurch(DeleteMaazounMaazouniaChurchRequest deleteMaazounMaazouniaChurchRequest);
	
	public String findById(MaazounByIDRequest maazounByIDRequest);
	
	public String findByNationalId(MaazounByIDRequest maazounByIDRequest);
	
	public String findByMobileNumber(MaazounByIDRequest maazounByIDRequest);
	
	public String findByCardNumber(MaazounByIDRequest maazounByIDRequest);
	
	public  List<String> getAllMaazounsList(CustomUserDetails customUserDetails);
	
	public  List<String> getAllMaazounsListByStatus(boolean status);
	
	public List<String> getMaazouniaChurchsByMaazounId(MaazounByIDRequest maazounByIDRequest);
	
	public PagingDTO getMaazounCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest);
	
	public String closeMaazounProfileCustody(EditMaazounCloseCustody editMaazounCloseCustody);
	
	public  List<String> findByNationalIdOrNameOrMobile(SearchmaazounProfileRequest SearchmaazounProfileRequest);

	public String editMaazounBookQuota(AddEditMaazounProfileRequest addEditMaazounProfileRequest, String username);

	 

	
}

package br.com.bpadash.api.user.treatment;


import br.com.bpadash.dto.treatment.TreatmentPaCboDTO;
import br.com.bpadash.dto.treatment.TreatmentPaDTO;
import br.com.bpadash.dto.treatment.TreatmentPaDeleteDTO;
import br.com.bpadash.model.*;
import br.com.bpadash.model.treatment.RuleTreatmentPa;
import br.com.bpadash.model.treatment.RuleTreatmentPaCbo;
import br.com.bpadash.model.treatment.RuleTreatmentPaDelete;
import br.com.bpadash.params.bpa.ParamDateBpa;
import br.com.bpadash.params.treatment.ParamTreatmentPa;
import br.com.bpadash.params.treatment.ParamTreatmentPaCbo;
import br.com.bpadash.params.treatment.ParamTreatmentPaDelete;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.services.bpa.BpaService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import br.com.bpadash.services.treatment.RuleTreatmentPaCboService;
import br.com.bpadash.services.treatment.RuleTreatmentPaDeleteService;
import br.com.bpadash.services.treatment.RuleTreatmentPaService;
import br.com.bpadash.services.treatment.TreatmentFileService;
import br.com.bpadash.services.user.UserService;
import br.com.bpadash.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treatment")
public class TreatmentApi {

}

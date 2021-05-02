package ir.navaco.mcb.identification.service.business.api;

import ir.navaco.mcb.identification.model.dto.PersonalInformationDto;
import ir.navaco.mcb.identification.model.sdkentity.ChangePINResult;
import ir.navaco.mcb.identification.model.sdkentity.FingerIndexResult;
import ir.navaco.mcb.identification.model.sdkentity.SignatureResult;

public interface IdentificationService {

    PersonalInformationDto fingerPrintAuthenticationStk();

    FingerIndexResult getMocFingerIndex();

    PersonalInformationDto pinAuthenticationStk();

    ChangePINResult pinChangeOwn();

    SignatureResult signOwn();

    PersonalInformationDto fingerPrintAuthenticationOwn();

}

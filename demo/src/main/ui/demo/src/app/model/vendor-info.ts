import { ContactInfo } from '../model/contact-info';

export class VendorInfo {
    id: string = "";
    name: string = "";
    applicant: string = "";
    owner: string = "";
    address: string = "";
    tel: string = "";
    fax: string = "";
    remark: string = "";
    updateTime: string = "";
    contacts: ContactInfo[] = [];
}

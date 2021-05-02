import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { VendorInfo } from '../model/vendor-info';
import { ContactInfo } from '../model/contact-info';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VendorInfoService {
  private findAllUrl: string = "http://localhost:8080/vendors";
  private saveVendorUrl: string = "http://localhost:8080/saveVendor";
  private saveContactsUrl: string = "http://localhost:8080/saveContacts";
  private updateVendorUrl: string = "http://localhost:8080/updateVendor";
  private updateContactsUrl: string = "http://localhost:8080/updateContacts";
  private deleteVendorUrl: string = "http://localhost:8080/deleteVendor";
  private deleteContactsUrl: string = "http://localhost:8080/deleteContacts";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<VendorInfo[]> {
    return this.http.get<VendorInfo[]>(this.findAllUrl);
  }

  public saveVendor(newVendor: VendorInfo): Observable<VendorInfo[]> {
    return this.http.post<VendorInfo[]>(this.saveVendorUrl, newVendor);
  }

  // public saveContacts(contacts: ContactInfo[]) {
  //   return this.http.post<ContactInfo[]>(this.saveContactsUrl, {contacts: contacts});
  // }

  public updateVendor(vendor: VendorInfo) {
    return this.http.put<VendorInfo[]>(this.updateVendorUrl, vendor);
  }

  public updateContacts(contacts: ContactInfo[]) {
    return this.http.put<ContactInfo[]>(this.updateContactsUrl, contacts);
  }

  public deleteVendor(id: string): Observable<VendorInfo[]> {
    const url = `${this.deleteVendorUrl}/${id}`;
    return this.http.delete<VendorInfo[]>(url);
  }

  public deleteContacts(contacts: ContactInfo[]) {
    return this.http.post<ContactInfo[]>(this.deleteContactsUrl, contacts);
  }

}

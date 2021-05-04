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
  private updateVendorUrl: string = "http://localhost:8080/updateVendor";
  private deleteVendorUrl: string = "http://localhost:8080/deleteVendor";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<VendorInfo[]> {
    return this.http.get<VendorInfo[]>(this.findAllUrl);
  }

  public saveVendor(newVendor: VendorInfo): Observable<VendorInfo[]> {
    return this.http.post<VendorInfo[]>(this.saveVendorUrl, newVendor);
  }

  public updateVendor(vendor: VendorInfo) {
    return this.http.put<VendorInfo[]>(this.updateVendorUrl, vendor);
  }

  public deleteVendor(id: string): Observable<VendorInfo[]> {
    const url = `${this.deleteVendorUrl}/${id}`;
    return this.http.delete<VendorInfo[]>(url);
  }

}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { VendorInfo } from '../model/vendor-info';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VendorInfoService {

  private vendorsUrl: string = "http://localhost:8080/vendors";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<VendorInfo[]> {
    return this.http.get<VendorInfo[]>(this.vendorsUrl);
  }

  // public save(vendor: VendorInfo) {
  //   return this.http.post<VendorInfo>(this.vendorsUrl, vendor);
  // }

}

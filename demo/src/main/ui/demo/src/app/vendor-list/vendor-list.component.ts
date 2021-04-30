import { Component, OnInit, ViewChild } from '@angular/core';
import { VendorInfoService } from '../service/vendor-info.service';
import { VendorInfo } from '../model/vendor-info';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-vendor-list',
  templateUrl: './vendor-list.component.html',
  styleUrls: ['./vendor-list.component.css']
})
export class VendorListComponent implements OnInit {
  @ViewChild(MatTable,{static:true}) table?: MatTable<any>;
  vendors: VendorInfo[] = [];
  displayedColumns: string[] = ['id', 'name', 'tel', 'applicant', 'updateTime', 'action'];
  dataSource = new MatTableDataSource<VendorInfo>(this.vendors);

  constructor(private vendorInfoService: VendorInfoService) { }

  ngOnInit(): void {
    this.vendorInfoService.findAll().subscribe(data => {
      this.vendors = data;
    });
  }
  deleteVendor(vendor: VendorInfo, rowIndex: number) {
    console.log("delete: ", vendor);
    this.vendors.splice(rowIndex, 1);
    this.table!.renderRows();
  }

}

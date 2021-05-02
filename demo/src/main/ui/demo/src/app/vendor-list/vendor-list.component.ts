import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { VendorInfoService } from '../service/vendor-info.service';
import { VendorInfo } from '../model/vendor-info';
import { ContactInfo } from '../model/contact-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-vendor-list',
  templateUrl: './vendor-list.component.html',
  styleUrls: ['./vendor-list.component.css']
})
export class VendorListComponent implements OnInit {
  @ViewChild('vendorListTable') vendorListTable?: MatTable<any>;
  @ViewChild('contactsTable') contactsTable?: MatTable<any>;
  // for list
  vendors: VendorInfo[] = [];
  displayedColumnsForList: string[] = ['id', 'name', 'tel', 'applicant', 'updateTime', 'action'];
  // dataSource = new MatTableDataSource<VendorInfo>(this.vendors);

  // for form
  vendor: VendorInfo = new VendorInfo();
  contact: ContactInfo = new ContactInfo();
  contacts: ContactInfo[] = [];
  displayedColumns: string[] = ['contactPerson', 'title', 'contactNumber', 'email', 'action'];
  editAction: boolean = false;

  constructor(
    private vendorInfoService: VendorInfoService,
    private contactDialog: MatDialog,
    private vendorDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.vendorInfoService.findAll().subscribe(data => {
      this.vendors = data;
    });
    this.contacts.push(new ContactInfo());
  }
  deleteVendor(vendor: VendorInfo, rowIndex: number) {
    console.log("delete: ", vendor);
    this.vendorInfoService.deleteVendor(vendor.id).subscribe(data => {
      console.log("delete success: ", data);
      this.vendors = data;
      this.vendorListTable!.renderRows();
    });
    // this.vendors.splice(rowIndex, 1);
    // this.table!.renderRows();
  }
  // editVendor(vendor: VendorInfo) {
  //   console.log("edit: ", vendor);
  //   this.vendorInfoService.updateVendor(vendor).subscribe(data => {
  //     console.log("edit success: ", data);
  //     this.vendors = data;
  //     this.vendorListTable!.renderRows();
  //   });
  //   // this.vendors.splice(rowIndex, 1);
  //   // this.table!.renderRows();
  // }
  showEditData(obj: VendorInfo, rowIndex: number) {
    this.vendor = obj;
    this.contacts = obj.contacts;
    this.contacts.push(new ContactInfo());
    this.editAction = true;
  }
  openVendorDialog(obj: VendorInfo, rowIndex: number) {
    const dialogRef = this.vendorDialog.open(VendorDialogComponent, {
      width: '60vw',
      disableClose: true,
      data: obj
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        console.log("result", result);
        // this.editVendor(result);
      }
    });
  }

  /***** form *****/
  onSubmit() {
    console.log(this.vendor, this.contacts);
    this.contacts.pop(); // remove empty row
    this.vendor.contacts = this.contacts;
    if(!this.editAction) {
      this.vendorInfoService.saveVendor(this.vendor).subscribe(data => {
          console.log("save success: ", data);
          // update data
          this.vendors = data;
          this.vendorListTable!.renderRows();
      });
    } else {
      this.vendorInfoService.updateVendor(this.vendor).subscribe(data => {
          console.log("edit success: ", data);
          // update data
          this.vendors = data;
          this.vendorListTable!.renderRows();
          this.editAction = false;
      });
    }
    this.resetContacts();
  }

  resetContacts() {
    this.contacts = [];
    this.contacts.push(new ContactInfo);
  }

  addContact(newContact: ContactInfo) {
    console.log(newContact);
    this.contacts.push(new ContactInfo());
    this.contactsTable!.renderRows();
  }

  editContact(editContact: ContactInfo, rowIndex: number) {
    console.log(editContact);
    this.contacts[rowIndex] = editContact;
  }

  deleteContact(deleteContact: ContactInfo, rowIndex: number) {
    console.log(deleteContact);
    this.contacts.splice(rowIndex, 1);
    this.contactsTable!.renderRows();
  }

  openDialog(action: string, obj: ContactInfo, rowIndex: number) {
    const dialogRef = this.contactDialog.open(ContactDialogComponent, {
      width: '500px',
      disableClose: true,
      data: {
        action: action,
        contact: obj
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        console.log("result", result);
        if(result.action == 'Add'){
          this.addContact(result.contact);
        } else if(result.action == 'Edit')  {
          this.editContact(result.contact, rowIndex);
        } else if(result.action == 'Delete')  {
          this.deleteContact(result.contact, rowIndex);
        }
      }
    });
  }

}

@Component({
    selector: 'contact-dialog',
    templateUrl: 'contact-dialog.html',
  })
  export class ContactDialogComponent {
    // contact: ContactInfo = new ContactInfo();
    constructor(
      public dialogRef: MatDialogRef<ContactDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any
    ) {
      // this.contact.contactPerson = data.contact.contactPerson;
      // this.contact.title = data.contact.title;
      // this.contact.contactNumber = data.contact.contactNumber;
      // this.contact.email = data.contact.email;
    }

    cancel(obj: any) {
      console.log("cancel ", obj);
      if(this.data.action == "Add") {
        this.data.contact.contactPerson = "";
        this.data.contact.title = "";
        this.data.contact.contactNumber = "";
        this.data.contact.email = "";
      }
      this.dialogRef.close();
    }
}

@Component({
    selector: 'vendor-dialog',
    templateUrl: 'vendor-dialog.html',
  })
  export class VendorDialogComponent {
    // contact: ContactInfo = new ContactInfo();
    constructor(
      public dialogRef: MatDialogRef<VendorDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public vendor: VendorInfo
    ) {
      // this.contact.contactPerson = data.contact.contactPerson;
      // this.contact.title = data.contact.title;
      // this.contact.contactNumber = data.contact.contactNumber;
      // this.contact.email = data.contact.email;
    }

    cancel(obj: any) {
      console.log("cancel ", obj);
      this.dialogRef.close();
    }
}

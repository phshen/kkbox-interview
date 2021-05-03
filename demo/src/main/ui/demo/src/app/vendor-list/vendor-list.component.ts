import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { VendorInfoService } from '../service/vendor-info.service';
import { VendorInfo } from '../model/vendor-info';
import { ContactInfo } from '../model/contact-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, Validators } from '@angular/forms';

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

  // phoneFormControl = new FormControl('', [
  //   Validators.required, Validators.pattern('^\\([0-9]{2}\\)[0-9]{8}')
  // ]);

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

  showEditData(obj: VendorInfo, rowIndex: number) {
    const vendorCopy = JSON.parse(JSON.stringify(obj));
    this.vendor = vendorCopy;
    // const contacts: ContactInfo[] = [];
    // contacts.push(obj.contacts);
    // contacts.push(new ContactInfo());
    this.contacts = this.vendor.contacts
    this.contacts.push(new ContactInfo());
    this.editAction = true;
  }

  /***** form *****/
  onSubmit() {
    console.log(this.vendor, this.contacts);
    if(!this.checkFormat(this.vendor.id)) return;
    this.contacts.pop(); // remove empty row
    this.vendor.contacts = this.contacts;
    if(!this.editAction) {
      this.vendorInfoService.saveVendor(this.vendor).subscribe(data => {
          console.log("save success: ", data);
          // update data
          this.vendors = data;
          this.vendorListTable!.renderRows();
      }, error => {
        console.log(error);
        if(error.status == 409) {
          window.alert("統一編號不可重複");
        } else {
          window.alert(error.error);
        }

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

  checkFormat(id: string): boolean {
    const regex = new RegExp('\\d{8}');
    if(!regex.test(id)) {
      window.alert("統一編號格式錯誤");
      return false;
    }
    return true;
  }

  resetContacts() {
    this.contacts = [];
    this.contacts.push(new ContactInfo);
    this.editAction = false;
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
    templateUrl: './contact-dialog.html',
    styleUrls: ['./vendor-list.component.css']
  })
  export class ContactDialogComponent {
    // contact: ContactInfo = new ContactInfo();
    constructor(
      public dialogRef: MatDialogRef<ContactDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any
    ) {
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


import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { VendorInfo } from '../model/vendor-info';
import { VendorInfoService } from '../service/vendor-info.service';
import { ContactInfo } from '../model/contact-info';
import { MatTable } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-vendor-form',
  templateUrl: './vendor-form.component.html',
  styleUrls: ['./vendor-form.component.css']
})
export class VendorFormComponent implements OnInit {
  @ViewChild(MatTable,{static:true}) table?: MatTable<any>;
  vendor: VendorInfo = new VendorInfo();
  contact: ContactInfo = new ContactInfo();
  contacts: ContactInfo[] = [];
  displayedColumns: string[] = ['contactPerson', 'title', 'contactNumber', 'email', 'action'];
  constructor(
    private vendorInfoService: VendorInfoService,
    private contactDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.contacts.push(new ContactInfo());
  }

  onSubmit() {
    //this.userService.save(this.user).subscribe(result => this.gotoUserList());
    console.log(this.vendor, this.contacts);
  }

  addContact(newContact: ContactInfo) {
    console.log(newContact);
    this.contacts.push(new ContactInfo());
    this.table!.renderRows();
  }

  editContact(editContact: ContactInfo, rowIndex: number) {
    console.log(editContact);
    this.contacts[rowIndex] = editContact;
  }

  deleteContact(deleteContact: ContactInfo, rowIndex: number) {
    console.log(deleteContact);
    this.contacts.splice(rowIndex, 1);
    this.table!.renderRows();
  }

  openDialog(action: string, obj: ContactInfo, rowIndex: number) {
    const dialogRef = this.contactDialog.open(ContactDialogComponent, {
      width: '250px',
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

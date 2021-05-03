import { Component, OnInit, ViewChild, Inject, Directive, Input } from '@angular/core';
import { VendorInfoService } from '../service/vendor-info.service';
import { VendorInfo } from '../model/vendor-info';
import { ContactInfo } from '../model/contact-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
// import { FormControl, Validators } from '@angular/forms';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';
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
          window.alert("儲存成功");
      }, error => {
        console.log(error);
        if(error.status == 409) {
          window.alert("統一編號已存在");
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
      window.alert("統一編號須為八位數字");
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

export function telValidator(nameRe: RegExp): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const valid = control.pristine || (control.touched && control.value.length > 0 && nameRe.test(control.value));
    return valid ? null : {telNumber: {value: control.value}};
  };
}

@Directive({
  selector: '[telValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: TelValidatorDirective, multi: true}]
})
export class TelValidatorDirective implements Validator {
  @Input('telValidator') telNumber?: string;

  validate(control: AbstractControl): ValidationErrors | null {
    return this.telNumber ? telValidator(new RegExp('^\\d{2,3}-\\d{7,8}$'))(control)
                              : null;
  }
}

export function companyIdValidator(re: RegExp): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const valid = control.pristine || (control.touched && control.value.length > 0 && re.test(control.value));
    return valid ? null : {companyId: {value: control.value}};
  };
}

@Directive({
  selector: '[companyIdValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: CompanyIdValidatorDirective, multi: true}]
})
export class CompanyIdValidatorDirective implements Validator {
  @Input('companyIdValidator') companyId?: string;

  validate(control: AbstractControl): ValidationErrors | null {
    return this.companyId ? companyIdValidator(new RegExp('^\\d{8}$'))(control)
                              : null;
  }
}

export function phoneValidator(nameRe: RegExp): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const valid = control.pristine || (control.touched && control.value.length > 0 && nameRe.test(control.value));
    return valid ? null : {phoneNumber: {value: control.value}};
  };
}

@Directive({
  selector: '[phoneValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: PhoneValidatorDirective, multi: true}]
})
export class PhoneValidatorDirective implements Validator {
  @Input('phoneValidator') phoneNumber?: string;

  validate(control: AbstractControl): ValidationErrors | null {
    return this.phoneNumber ? phoneValidator(new RegExp('^\\d{4}-\\d{6}$'))(control)
                              : null;
  }
}
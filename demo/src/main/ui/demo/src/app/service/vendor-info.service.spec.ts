import { TestBed } from '@angular/core/testing';

import { VendorInfoService } from './vendor-info.service';

describe('VendorInfoService', () => {
  let service: VendorInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pay } from './pay.model';
import { PayService } from './pay.service';
@Injectable()
export class PayPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private payService: PayService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.payService.find(id).subscribe(pay => {
                if (pay.payDate) {
                    pay.payDate = {
                        year: pay.payDate.getFullYear(),
                        month: pay.payDate.getMonth() + 1,
                        day: pay.payDate.getDate()
                    };
                }
                this.payModalRef(component, pay);
            });
        } else {
            return this.payModalRef(component, new Pay());
        }
    }

    payModalRef(component: Component, pay: Pay): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pay = pay;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}

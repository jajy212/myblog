import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MemOrder } from './mem-order.model';
import { MemOrderService } from './mem-order.service';
@Injectable()
export class MemOrderPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private memOrderService: MemOrderService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.memOrderService.find(id).subscribe(memOrder => {
                if (memOrder.createdDate) {
                    memOrder.createdDate = {
                        year: memOrder.createdDate.getFullYear(),
                        month: memOrder.createdDate.getMonth() + 1,
                        day: memOrder.createdDate.getDate()
                    };
                }
                if (memOrder.lastModifiedDate) {
                    memOrder.lastModifiedDate = {
                        year: memOrder.lastModifiedDate.getFullYear(),
                        month: memOrder.lastModifiedDate.getMonth() + 1,
                        day: memOrder.lastModifiedDate.getDate()
                    };
                }
                this.memOrderModalRef(component, memOrder);
            });
        } else {
            return this.memOrderModalRef(component, new MemOrder());
        }
    }

    memOrderModalRef(component: Component, memOrder: MemOrder): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.memOrder = memOrder;
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

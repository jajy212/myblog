import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MemberCate } from './member-cate.model';
import { MemberCateService } from './member-cate.service';
@Injectable()
export class MemberCatePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private memberCateService: MemberCateService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.memberCateService.find(id).subscribe(memberCate => {
                this.memberCateModalRef(component, memberCate);
            });
        } else {
            return this.memberCateModalRef(component, new MemberCate());
        }
    }

    memberCateModalRef(component: Component, memberCate: MemberCate): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.memberCate = memberCate;
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

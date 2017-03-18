import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MemberCate } from './member-cate.model';
import { MemberCatePopupService } from './member-cate-popup.service';
import { MemberCateService } from './member-cate.service';
@Component({
    selector: 'jhi-member-cate-dialog',
    templateUrl: './member-cate-dialog.component.html'
})
export class MemberCateDialogComponent implements OnInit {

    memberCate: MemberCate;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private memberCateService: MemberCateService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.memberCate.id !== undefined) {
            this.memberCateService.update(this.memberCate)
                .subscribe((res: MemberCate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.memberCateService.create(this.memberCate)
                .subscribe((res: MemberCate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: MemberCate) {
        this.eventManager.broadcast({ name: 'memberCateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-member-cate-popup',
    template: ''
})
export class MemberCatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private memberCatePopupService: MemberCatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.memberCatePopupService
                    .open(MemberCateDialogComponent, params['id']);
            } else {
                this.modalRef = this.memberCatePopupService
                    .open(MemberCateDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Pay } from './pay.model';
import { PayPopupService } from './pay-popup.service';
import { PayService } from './pay.service';
import { MemOrder, MemOrderService } from '../mem-order';
@Component({
    selector: 'jhi-pay-dialog',
    templateUrl: './pay-dialog.component.html'
})
export class PayDialogComponent implements OnInit {

    pay: Pay;
    authorities: any[];
    isSaving: boolean;

    memorders: MemOrder[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private payService: PayService,
        private memOrderService: MemOrderService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.memOrderService.query().subscribe(
            (res: Response) => { this.memorders = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.pay.id !== undefined) {
            this.payService.update(this.pay)
                .subscribe((res: Pay) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.payService.create(this.pay)
                .subscribe((res: Pay) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Pay) {
        this.eventManager.broadcast({ name: 'payListModification', content: 'OK'});
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

    trackMemOrderById(index: number, item: MemOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pay-popup',
    template: ''
})
export class PayPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private payPopupService: PayPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.payPopupService
                    .open(PayDialogComponent, params['id']);
            } else {
                this.modalRef = this.payPopupService
                    .open(PayDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

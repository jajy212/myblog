import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MemOrder } from './mem-order.model';
import { MemOrderPopupService } from './mem-order-popup.service';
import { MemOrderService } from './mem-order.service';
import { Pay, PayService } from '../pay';
import { Goods, GoodsService } from '../goods';
import { Member, MemberService } from '../member';
@Component({
    selector: 'jhi-mem-order-dialog',
    templateUrl: './mem-order-dialog.component.html'
})
export class MemOrderDialogComponent implements OnInit {

    memOrder: MemOrder;
    authorities: any[];
    isSaving: boolean;

    pays: Pay[];

    goods: Goods[];

    members: Member[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private memOrderService: MemOrderService,
        private payService: PayService,
        private goodsService: GoodsService,
        private memberService: MemberService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.payService.query().subscribe(
            (res: Response) => { this.pays = res.json(); }, (res: Response) => this.onError(res.json()));
        this.goodsService.query().subscribe(
            (res: Response) => { this.goods = res.json(); }, (res: Response) => this.onError(res.json()));
        this.memberService.query().subscribe(
            (res: Response) => { this.members = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.memOrder.id !== undefined) {
            this.memOrderService.update(this.memOrder)
                .subscribe((res: MemOrder) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.memOrderService.create(this.memOrder)
                .subscribe((res: MemOrder) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: MemOrder) {
        this.eventManager.broadcast({ name: 'memOrderListModification', content: 'OK'});
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

    trackPayById(index: number, item: Pay) {
        return item.id;
    }

    trackGoodsById(index: number, item: Goods) {
        return item.id;
    }

    trackMemberById(index: number, item: Member) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-mem-order-popup',
    template: ''
})
export class MemOrderPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private memOrderPopupService: MemOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.memOrderPopupService
                    .open(MemOrderDialogComponent, params['id']);
            } else {
                this.modalRef = this.memOrderPopupService
                    .open(MemOrderDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

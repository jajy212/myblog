import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Member } from './member.model';
import { MemberPopupService } from './member-popup.service';
import { MemberService } from './member.service';
import { MemOrder, MemOrderService } from '../mem-order';
import { Grade, GradeService } from '../grade';
import { MemberCate, MemberCateService } from '../member-cate';
@Component({
    selector: 'jhi-member-dialog',
    templateUrl: './member-dialog.component.html'
})
export class MemberDialogComponent implements OnInit {

    member: Member;
    authorities: any[];
    isSaving: boolean;

    memorders: MemOrder[];

    grades: Grade[];

    membercates: MemberCate[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private memberService: MemberService,
        private memOrderService: MemOrderService,
        private gradeService: GradeService,
        private memberCateService: MemberCateService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.memOrderService.query().subscribe(
            (res: Response) => { this.memorders = res.json(); }, (res: Response) => this.onError(res.json()));
        this.gradeService.query().subscribe(
            (res: Response) => { this.grades = res.json(); }, (res: Response) => this.onError(res.json()));
        this.memberCateService.query().subscribe(
            (res: Response) => { this.membercates = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.member.id !== undefined) {
            this.memberService.update(this.member)
                .subscribe((res: Member) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.memberService.create(this.member)
                .subscribe((res: Member) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Member) {
        this.eventManager.broadcast({ name: 'memberListModification', content: 'OK'});
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

    trackGradeById(index: number, item: Grade) {
        return item.id;
    }

    trackMemberCateById(index: number, item: MemberCate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-member-popup',
    template: ''
})
export class MemberPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private memberPopupService: MemberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.memberPopupService
                    .open(MemberDialogComponent, params['id']);
            } else {
                this.modalRef = this.memberPopupService
                    .open(MemberDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

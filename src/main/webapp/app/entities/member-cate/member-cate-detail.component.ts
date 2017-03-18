import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MemberCate } from './member-cate.model';
import { MemberCateService } from './member-cate.service';

@Component({
    selector: 'jhi-member-cate-detail',
    templateUrl: './member-cate-detail.component.html'
})
export class MemberCateDetailComponent implements OnInit, OnDestroy {

    memberCate: MemberCate;
    private subscription: any;

    constructor(
        private memberCateService: MemberCateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.memberCateService.find(id).subscribe(memberCate => {
            this.memberCate = memberCate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}

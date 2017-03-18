import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pay } from './pay.model';
import { PayService } from './pay.service';

@Component({
    selector: 'jhi-pay-detail',
    templateUrl: './pay-detail.component.html'
})
export class PayDetailComponent implements OnInit, OnDestroy {

    pay: Pay;
    private subscription: any;

    constructor(
        private payService: PayService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.payService.find(id).subscribe(pay => {
            this.pay = pay;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}

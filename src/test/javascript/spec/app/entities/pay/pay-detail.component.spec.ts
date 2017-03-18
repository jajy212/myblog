import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PayDetailComponent } from '../../../../../../main/webapp/app/entities/pay/pay-detail.component';
import { PayService } from '../../../../../../main/webapp/app/entities/pay/pay.service';
import { Pay } from '../../../../../../main/webapp/app/entities/pay/pay.model';

describe('Component Tests', () => {

    describe('Pay Management Detail Component', () => {
        let comp: PayDetailComponent;
        let fixture: ComponentFixture<PayDetailComponent>;
        let service: PayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PayDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    PayService
                ]
            }).overrideComponent(PayDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pay(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pay).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

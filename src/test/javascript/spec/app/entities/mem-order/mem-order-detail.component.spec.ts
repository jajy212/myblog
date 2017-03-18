import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MemOrderDetailComponent } from '../../../../../../main/webapp/app/entities/mem-order/mem-order-detail.component';
import { MemOrderService } from '../../../../../../main/webapp/app/entities/mem-order/mem-order.service';
import { MemOrder } from '../../../../../../main/webapp/app/entities/mem-order/mem-order.model';

describe('Component Tests', () => {

    describe('MemOrder Management Detail Component', () => {
        let comp: MemOrderDetailComponent;
        let fixture: ComponentFixture<MemOrderDetailComponent>;
        let service: MemOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MemOrderDetailComponent],
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
                    MemOrderService
                ]
            }).overrideComponent(MemOrderDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemOrderService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MemOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.memOrder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

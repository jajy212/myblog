import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MemberCateDetailComponent } from '../../../../../../main/webapp/app/entities/member-cate/member-cate-detail.component';
import { MemberCateService } from '../../../../../../main/webapp/app/entities/member-cate/member-cate.service';
import { MemberCate } from '../../../../../../main/webapp/app/entities/member-cate/member-cate.model';

describe('Component Tests', () => {

    describe('MemberCate Management Detail Component', () => {
        let comp: MemberCateDetailComponent;
        let fixture: ComponentFixture<MemberCateDetailComponent>;
        let service: MemberCateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MemberCateDetailComponent],
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
                    MemberCateService
                ]
            }).overrideComponent(MemberCateDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemberCateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemberCateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MemberCate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.memberCate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

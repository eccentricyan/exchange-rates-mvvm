package eu.rampsoftware.er.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NoArgQueryUseCaseTest {


    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    private Scheduler mWorkScheduler;
    private Scheduler mObserveScheduler;
    private NoArgQueryUseCaseTestClass mUseCase;
    private TestDisposableObserver<Object> mTestObserver;


    @Before
    public void setUp() {
        mWorkScheduler = new TestScheduler();
        mObserveScheduler = new TestScheduler();
        this.mUseCase = new NoArgQueryUseCaseTestClass(mWorkScheduler, mObserveScheduler);
        this.mTestObserver = new TestDisposableObserver<>();
    }

    @Test
    public void thatObserverDisposed() {
        mUseCase.run(mTestObserver);

        mUseCase.dispose();

        assertThat(mTestObserver.isDisposed()).isTrue();
    }

    @Test
    public void thatExceptionThrownOnNullObserver() {
        mExpectedException.expect(NullPointerException.class);
        mUseCase.run(null);
    }

    private static class NoArgQueryUseCaseTestClass extends NoArgQueryUseCase<Object> {
        NoArgQueryUseCaseTestClass(final Scheduler workScheduler, final Scheduler observeScheduler) {
            super(workScheduler, observeScheduler);
        }

        @Override
        protected Observable<Object> buildUseCaseObservable() {
            return Observable.empty();
        }
    }

    private static class Params {
        public static Params EMPTY = new Params();
    }
}

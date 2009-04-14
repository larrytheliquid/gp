(ns gp.problems.complex-atm
  (:refer-clojure :exclude [test assert])
  (:use easd))

;;; maybe 'security-flag'

(test charge?
  [statement-printed money-withdrawn money-deposited]
  ("true when money was withdrawn"
   (assert * 1 *))
  ("true when a statement was printed but no money was deposited"
   (assert 1 * 0)))

(test email?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account]
  ("true when money was withdrawn but a statement was not printed"
   (assert * 1 *))
  ("true when money was deposited but a statement was not printed"
   (assert 1 * 0)))

(test surcharge?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account]
  ("true when money was withdrawn for a non-member without a premium account"
   (assert * 1 * 1 0)))

(test reward-points?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account]
  ("true when a premium account deposits money"
   (assert * * 1 * 1)))

(test confirmation?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account checking-account savings-account security-flag]
  ("true when money was withdrawn from a savings-account"
   (assert)))

(test video-snapshot?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account checking-account savings-account security-flag]
  ("true when money was withdrawn and the security flag is set"
   (assert))
  ("true when a statement was printed and the security flag is set"
   (assert)))

(test advertise-plans?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account checking-account savings-account security-flag]
  )

(test record-statistic?
  [statement-printed money-withdrawn money-deposited non-member
   premium-account checking-account savings-account security-flag]
  ("true when a non-member makes a transaction"
   (assert))
  ("true when the security flag is set"
   (assert))
  ("true when a premium-account makes a transaction"
   (assert)))



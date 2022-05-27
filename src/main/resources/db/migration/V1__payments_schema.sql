
CREATE TABLE public.payments(
    id                      uuid NOT NULL,
    card_token              VARCHAR NOT NULL,
    payment_method          VARCHAR NOT NULL,
    amount                  DECIMAL(10,2) NOT NULL,
    payment_reference       VARCHAR NOT NULL,
    status                  VARCHAR NOT NULL,
    CONSTRAINT payments_pkey PRIMARY KEY (id)
);

package br.dev.tmn.cryptocurrency.data.mapper

import br.dev.tmn.cryptocurrency.data.service.response.Datum
import br.dev.tmn.cryptocurrency.data.service.response.Quote
import br.dev.tmn.cryptocurrency.data.service.response.USD
import br.dev.tmn.cryptocurrency.domain.entities.Crypto

class CryptoMapperService : BaseMapperRepository<Datum, Crypto> {

    override fun transform(type: Datum) =
        Crypto(
            id = type.id,
            name = type.name,
            price = type.quote.usd.price,
            percentChange1H = type.quote.usd.percentChange1H,
            percentChange24H = type.quote.usd.percentChange24H,
            percentChange7D = type.quote.usd.percentChange7D
        )

    override fun transformToRepository(type: Crypto) =
        Datum(
            id = type.id,
            name = type.name,
            quote = Quote(
                USD(
                    price = type.price,
                    percentChange1H = type.percentChange1H,
                    percentChange24H = type.percentChange24H,
                    percentChange7D = type.percentChange7D
                )
            )
        )
}